package com.othmane.awsimageupload.profile;

import com.othmane.awsimageupload.bucket.BucketName;
import com.othmane.awsimageupload.filestore.FileStore;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserProfileService {
    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }

    List<UserProfile> getUserProfiles() {
        return userProfileDataAccessService.getUserProfiles();
    }

    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        isEmpty(file);
        isImage(file);
        var user = findUser(userProfileId);
        var metaData = getMetaData(file);
        var path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), userProfileId);
        var fileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
        try {
            fileStore.save(path, fileName, Optional.of(metaData), file.getInputStream());
            user.setUserProfileImageLink(fileName);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public byte[] downloadUserProfileImage(UUID userProfileId) {
        var user = findUser(userProfileId);
        var path = String.format("%s/%s",
                BucketName.PROFILE_IMAGE.getBucketName(),
                user.getUserProfileId());
        return user.getUserProfileImageLink().map(key -> fileStore.download(path, key))
                .orElse(new byte[0]);
    }

    private HashMap<String, String> getMetaData(MultipartFile file) {
        var metaData = new HashMap<String, String>();
        metaData.put("Content-Type", file.getContentType());
        metaData.put("Content-Length", String.valueOf(file.getSize()));
        return metaData;
    }

    private UserProfile findUser(UUID userProfileId) {
        return this.userProfileDataAccessService.
                getUserProfiles()
                .stream()
                .filter(u -> u.getUserProfileId().equals(userProfileId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User Unfound " + userProfileId));
    }

    private void isImage(MultipartFile file) {
        if (!Arrays.asList(ContentType.IMAGE_PNG.getMimeType(), ContentType.IMAGE_JPEG.getMimeType(), ContentType.IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("Content type doesn't respect allowed types [ " + file.getContentType() + " ]");
        }
    }

    private void isEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Content file is empty [ " + file.getSize() + " ]");
        }
    }
}



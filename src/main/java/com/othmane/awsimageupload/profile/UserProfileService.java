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
        if (file.isEmpty()) {
            throw new IllegalStateException("Content file is empty [ " + file.getSize() + " ]");
        }
        if (Arrays.asList(ContentType.IMAGE_PNG, ContentType.IMAGE_JPEG, ContentType.IMAGE_GIF).contains(file.getContentType())) {
            throw new IllegalStateException("Content type doesn't respect allowed types [ " + file.getSize());
        }
        var user = this.userProfileDataAccessService.
                getUserProfiles()
                .stream()
                .filter(u -> u.getUserProfileId().equals(userProfileId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User Unfound " + userProfileId));
        var metaData = new HashMap<String, String>();
        metaData.put("Content-Type", file.getContentType());
        metaData.put("Centent-Length", String.valueOf(file.getSize()));
        var path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), userProfileId);
        var fileName = String.format("%s-%s", file.getName(), UUID.randomUUID());
        try {
            fileStore.save(path, fileName, Optional.of(metaData), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}



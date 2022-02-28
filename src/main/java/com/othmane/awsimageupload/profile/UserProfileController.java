package com.othmane.awsimageupload.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/user-profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public List<UserProfile> getUserProfiles() {
        return userProfileService.getUserProfiles();
    }

    public void uploadUserProfileImage(@PathVariable("userProfileId") UUID userProfileId, @RequestParam("file") MultipartFile file) {
        this.userProfileService.uploadUserProfileImage(userProfileId,file);
    }
}

package com.othmane.awsimageupload.datastore;

import com.othmane.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {
    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

    static {
        USER_PROFILES.add(new UserProfile(UUID.fromString("8369317a-9a76-4382-9a05-97bbc5a789f1"), "daryldixon", null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("637493c7-2cf4-4008-88e7-4f6966e2e017"), "jhonwick", null));
    }

    public List<UserProfile> getUserProfiles() {
        System.out.println(USER_PROFILES);
        return USER_PROFILES;
    }
}

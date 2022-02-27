package com.othmane.awsimageupload.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class UserProfile {
    private UUID userProfilId;
    private String username;
    // s3 key
    private String userProfileImageLink;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(userProfilId, that.userProfilId)
                && Objects.equals(username, that.username)
                && Objects.equals(userProfileImageLink, that.userProfileImageLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userProfilId, username, userProfileImageLink);
    }
}

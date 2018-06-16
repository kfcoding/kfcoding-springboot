package com.cuiyun.kfcoding.rest.modular.common.model;

import java.io.Serializable;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cuiyun.kfcoding.rest.modular.base.enums.ThirdpartAuthTypeEnum;

/**
 * <p>
 * 
 * </p>
 *
 * @author maple123
 * @since 2018-05-19
 */
@TableName("common_thirdpart")
public class Thirdpart extends Model<Thirdpart> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;
    @TableField("user_id")
    private String userId;
    @JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
    @TableField("auth_type")
    private ThirdpartAuthTypeEnum authType;
    @TableField("thirdpart_id")
    private String thirdpartId;
    @TableField("gists_url")
    private String gistsUrl;
    @TableField("repos_url")
    private String reposUrl;
    @TableField("following_url")
    private String followingUrl;
    private String bio;
    @TableField("created_at")
    private Date createdAt;
    private String login;
    private String type;
    private String blog;
    @TableField("subscriptions_url")
    private String subscriptionsUrl;
    @TableField("updated_at")
    private Date updatedAt;
    @TableField("site_admin")
    private String siteAdmin;
    private String company;
    @TableField("public_repos")
    private String publicRepos;
    @TableField("gravatar_id")
    private String gravatarId;
    private String email;
    @TableField("organizations_url")
    private String organizationsUrl;
    private String hireable;
    @TableField("starred_url")
    private String starredUrl;
    @TableField("followers_url")
    private String followersUrl;
    @TableField("public_gists")
    private Integer publicGists;
    private String url;
    @TableField("access_token")
    private String accessToken;
    @TableField("received_events_url")
    private String receivedEventsUrl;
    private Integer followers;
    @TableField("avatar_url")
    private String avatarUrl;
    @TableField("events_url")
    private String eventsUrl;
    @TableField("html_url")
    private String htmlUrl;
    private Integer following;
    private String name;
    private String location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ThirdpartAuthTypeEnum getAuthType() {
        return authType;
    }

    public void setAuthType(ThirdpartAuthTypeEnum authType) {
        this.authType = authType;
    }

    public String getThirdpartId() {
        return thirdpartId;
    }

    public void setThirdpartId(String thirdpartId) {
        this.thirdpartId = thirdpartId;
    }

    public String getGistsUrl() {
        return gistsUrl;
    }

    public void setGistsUrl(String gistsUrl) {
        this.gistsUrl = gistsUrl;
    }

    public String getReposUrl() {
        return reposUrl;
    }

    public void setReposUrl(String reposUrl) {
        this.reposUrl = reposUrl;
    }

    public String getFollowingUrl() {
        return followingUrl;
    }

    public void setFollowingUrl(String followingUrl) {
        this.followingUrl = followingUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getSubscriptionsUrl() {
        return subscriptionsUrl;
    }

    public void setSubscriptionsUrl(String subscriptionsUrl) {
        this.subscriptionsUrl = subscriptionsUrl;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSiteAdmin() {
        return siteAdmin;
    }

    public void setSiteAdmin(String siteAdmin) {
        this.siteAdmin = siteAdmin;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPublicRepos() {
        return publicRepos;
    }

    public void setPublicRepos(String publicRepos) {
        this.publicRepos = publicRepos;
    }

    public String getGravatarId() {
        return gravatarId;
    }

    public void setGravatarId(String gravatarId) {
        this.gravatarId = gravatarId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganizationsUrl() {
        return organizationsUrl;
    }

    public void setOrganizationsUrl(String organizationsUrl) {
        this.organizationsUrl = organizationsUrl;
    }

    public String getHireable() {
        return hireable;
    }

    public void setHireable(String hireable) {
        this.hireable = hireable;
    }

    public String getStarredUrl() {
        return starredUrl;
    }

    public void setStarredUrl(String starredUrl) {
        this.starredUrl = starredUrl;
    }

    public String getFollowersUrl() {
        return followersUrl;
    }

    public void setFollowersUrl(String followersUrl) {
        this.followersUrl = followersUrl;
    }

    public Integer getPublicGists() {
        return publicGists;
    }

    public void setPublicGists(Integer publicGists) {
        this.publicGists = publicGists;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getReceivedEventsUrl() {
        return receivedEventsUrl;
    }

    public void setReceivedEventsUrl(String receivedEventsUrl) {
        this.receivedEventsUrl = receivedEventsUrl;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getEventsUrl() {
        return eventsUrl;
    }

    public void setEventsUrl(String eventsUrl) {
        this.eventsUrl = eventsUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public Integer getFollowing() {
        return following;
    }

    public void setFollowing(Integer following) {
        this.following = following;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Thirdpart{" +
        "id=" + id +
        ", userId=" + userId +
        ", thirdpartId=" + thirdpartId +
        ", gistsUrl=" + gistsUrl +
        ", reposUrl=" + reposUrl +
        ", followingUrl=" + followingUrl +
        ", bio=" + bio +
        ", createdAt=" + createdAt +
        ", login=" + login +
        ", type=" + type +
        ", blog=" + blog +
        ", subscriptionsUrl=" + subscriptionsUrl +
        ", updatedAt=" + updatedAt +
        ", siteAdmin=" + siteAdmin +
        ", company=" + company +
        ", publicRepos=" + publicRepos +
        ", gravatarId=" + gravatarId +
        ", email=" + email +
        ", organizationsUrl=" + organizationsUrl +
        ", hireable=" + hireable +
        ", starredUrl=" + starredUrl +
        ", followersUrl=" + followersUrl +
        ", publicGists=" + publicGists +
        ", url=" + url +
        ", accessToken=" + accessToken +
        ", receivedEventsUrl=" + receivedEventsUrl +
        ", followers=" + followers +
        ", avatarUrl=" + avatarUrl +
        ", eventsUrl=" + eventsUrl +
        ", htmlUrl=" + htmlUrl +
        ", following=" + following +
        ", name=" + name +
        ", location=" + location +
        "}";
    }
}

package com.example.vartikasharma.githubapi;

public class CommitItem {
    private String personImageUrl;
    private String personName;
    private String commitId;
    private String commitMessage;

    public CommitItem(String personImageUrl, String personName, String commitId, String commitMessage) {
        this.personImageUrl = personImageUrl;
        this.personName = personName;
        this.commitId = commitId;
        this.commitMessage = commitMessage;
    }

    public String getPersonImageUrl() {
        return personImageUrl;
    }

    public void setPersonImageUrl(String personImageUrl) {
        this.personImageUrl = personImageUrl;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }
}

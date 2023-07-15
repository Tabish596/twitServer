package com.twitter.server.models;

import lombok.Data;

@Data
public class TweetDetails {
	private String comments;
	private String reshare;
	private String likes;
	private String views;
	private String tweetId;
	private String bookMarks;
}

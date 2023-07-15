package com.twitter.server.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="tweets")
public class Tweet {
	private String content;
	private String ownerId;
	private String time;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String tweetId;
}

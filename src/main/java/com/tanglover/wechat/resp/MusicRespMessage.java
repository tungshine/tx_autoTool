package com.tanglover.wechat.resp;

/**
 * 音乐消息
 * 
 * @author TungShine 2016年9月26日
 */
public class MusicRespMessage extends BaseRespMessage {
	// 音乐
	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
}
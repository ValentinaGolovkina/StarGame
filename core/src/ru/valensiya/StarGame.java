package ru.valensiya;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import ru.valensiya.screen.MenuScreen;

public class StarGame extends Game {
	private Music music;
	@Override
	public void create () {
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
		music.play();
		music.setLooping(true);
		setScreen(new MenuScreen(this));
	}

	@Override
	public void pause() {
		music.pause();
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
		music.play();
	}

	@Override
	public void dispose() {
		music.dispose();
		super.dispose();
	}
}

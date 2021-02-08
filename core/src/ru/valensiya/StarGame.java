package ru.valensiya;

import com.badlogic.gdx.Game;

import ru.valensiya.screen.MenuScreen;

public class StarGame extends Game {
	
	@Override
	public void create () {
		setScreen(new MenuScreen());
	}

}

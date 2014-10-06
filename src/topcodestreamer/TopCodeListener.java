package topcodestreamer;

import java.util.List;

import topcodes.TopCode;

public interface TopCodeListener {
	
	public abstract void onTopCodeFound(List<TopCode> codes);
}

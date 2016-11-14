package uk.co.pbellchambers.maceswinger.server.GUI.commands;

import com.moomoohk.MooCommands.Command;
import uk.co.pbellchambers.maceswinger.server.GUI.MainFrame;

public class ClearCommand extends Command
{

	@Override
	public String getCommand()
	{
		return "/clear";
	}

	@Override
	public String getHelpMessage()
	{
		return "Clears the console window";
	}

	@Override
	public String getUsage()
	{
		return "/clear";
	}

	@Override
	public int getMaxParams()
	{
		return 0;
	}

	@Override
	public int getMinParams()
	{
		return 0;
	}

	@Override
	protected void execute(String[] params)
	{
		MainFrame.frame.consoleTextPane.setText("");
	}
}

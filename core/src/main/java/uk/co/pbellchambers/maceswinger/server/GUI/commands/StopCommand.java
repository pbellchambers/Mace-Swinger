package uk.co.pbellchambers.maceswinger.server.GUI.commands;

import com.moomoohk.MooCommands.Command;
import uk.co.pbellchambers.maceswinger.server.GUI.MainFrame;

import java.awt.*;

public class StopCommand extends Command {

    @Override
    public String getCommand() {
        return "/stop";
    }

    @Override
    public String getHelpMessage() {
        return "Stops the server";
    }

    @Override
    public String getUsage() {
        return "/stop";
    }

    @Override
    public int getMaxParams() {
        return 0;
    }

    @Override
    public int getMinParams() {
        return 0;
    }

    protected boolean check(String[] params) {
        if (!MainFrame.frame.server.isRunning()) {
            this.outputColor = Color.red;
            this.outputMessage = "Server isn't running!";
            return false;
        }
        return super.check(params);
    }

    @Override
    protected void execute(String[] params) {
        MainFrame.frame.server.stop();
    }
}

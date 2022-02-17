package frc.robot.commands.playback;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.wpilibj.Filesystem;

public class Playback {
    private static ObjectMapper jsonMapper = new ObjectMapper();
    private static File containerPath = Filesystem.getDeployDirectory();

    // NOTE: This class is JSON-serialized and deserialized!
    private PlaybackStep[] steps;

    public PlaybackStep[] getSteps() {
        return steps;
    }

    public void setSteps(PlaybackStep[] steps) {
        this.steps = steps;
    }

    public void save(String name) {
        File outputFile = new File(containerPath, name + ".json");
        try {
            jsonMapper.writeValue(outputFile, this);
        } catch (IOException e) {
            System.out.println("Could not write playback file");
            e.printStackTrace();
        }
    }

    public static Playback load(String name) {
        File file = new File(containerPath, name + ".json");
        try {
            return jsonMapper.readValue(file, Playback.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

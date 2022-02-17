package frc.robot.commands.playback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.wpilibj.Filesystem;

public class Playback {
    private static ObjectMapper jsonMapper = new ObjectMapper();
    private static File containerPath = Filesystem.getDeployDirectory();

    // NOTE: This class is JSON-serialized and deserialized!
    private List<PlaybackStep> steps = new ArrayList<>();

    public List<PlaybackStep> getSteps() {
        return steps;
    }

    public void addStep(PlaybackStep step) {
        steps.add(step);
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

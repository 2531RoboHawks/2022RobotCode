package frc.robot.commands.playback;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.wpilibj.Filesystem;
import frc.robot.PIDSettings;

public class Playback {
    private static ObjectMapper jsonMapper = new ObjectMapper();
    private static File containerPath = Filesystem.getDeployDirectory();

    // NOTE: This class is JSON-serialized and deserialized!
    public PlaybackStep[] steps;
    public PIDSettings pid;

    public PlaybackStep[] getSteps() {
        return steps;
    }
    public void setSteps(PlaybackStep[] steps) {
        this.steps = steps;
    }

    public PIDSettings getPID() {
        return pid;
    }
    public void setPID(PIDSettings pid) {
        this.pid = pid;
    }

    public void save(String name) {
        try {
            Path outputPath = containerPath.toPath().getParent().resolve(name + ".json");
            String json = jsonMapper.writeValueAsString(this);
            Files.writeString(outputPath, json);
            System.out.println("Saved to " + outputPath);
        } catch (IOException e) {
            System.out.println("Could not write playback file");
            e.printStackTrace();
        }
    }

    public static Playback load(String name) {
        try {
            Path inputPath = containerPath.toPath().resolve(name + ".json");
            Playback playback = jsonMapper.readValue(inputPath.toFile(), Playback.class);
            System.out.println(playback.getSteps().length);
            return playback;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

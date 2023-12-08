package com.example.demo.config;

import com.example.demo.model.Car;
import com.example.demo.repository.CarRepository;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalTime;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataConfig {

    private final CarRepository carRepository;

    @Autowired
    public DataConfig(CarRepository carRepository) {
        this.carRepository = carRepository;
        LoadUsers();
    }

    private void LoadUsers() {
        URL fileUrl = getClass().getClassLoader().getResource("measurements.txt");
        File file = new File(fileUrl.getFile());
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                carRepository.save(parseLineData(line));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Car parseLineData(String line) {
        String[] data = line.split(" ");

        String plate = data[0];

        int inHour = Integer.parseInt(data[1]);
        int inMinutes = Integer.parseInt(data[2]);
        int inSeconds = Integer.parseInt(data[3]);
        int inMilliSeconds = Integer.parseInt(data[4]);

        int outHour = Integer.parseInt(data[5]);
        int outMinutes = Integer.parseInt(data[6]);
        int outSeconds = Integer.parseInt(data[7]);
        int outMilliSeconds = Integer.parseInt(data[8]);

        LocalTime inTime = LocalTime.of(inHour, inMinutes, inSeconds, inMilliSeconds);
        LocalTime outTime = LocalTime.of(outHour, outMinutes, outSeconds, outMilliSeconds);

        return new Car(plate, inTime, outTime);
    }


    public LocalTime parseTimeFromInput(String line) {
        int hour = Integer.parseInt(line.split(" ")[0]);
        int minute = Integer.parseInt(line.split(" ")[1]);
        return LocalTime.of(hour, minute);
    }
}

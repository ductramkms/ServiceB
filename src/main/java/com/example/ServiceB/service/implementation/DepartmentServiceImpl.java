package com.example.ServiceB.service.implementation;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.example.ServiceB.service.DepartmentService;
import com.example.ServiceB.util.ColorLog;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.SneakyThrows;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final MeterRegistry meterRegistry;

    public DepartmentServiceImpl(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;

        Gauge.builder("departments_count", () -> 10)
                .description("A current number of departments in the company")
                .register(meterRegistry);
    }

    @Override
    @SneakyThrows
    public String all(String title) {
        Tag titleTag = Tag.of("title", StringUtils.isEmpty(title) ? "all" : title);
        Timer.Sample timer = Timer.start(meterRegistry);

        String result = "ZERO by Default";

        if (StringUtils.isEmpty(title)) {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));
            result = "ALL-DEPARTMENT";
        } else {
            if ("Software".equalsIgnoreCase(title)) {
                Thread.sleep(ThreadLocalRandom.current().nextInt(2000, 5000));
                result = "Software Engineering Department";
            }
        }

        timer.stop(Timer.builder("service_find_all_departments").description(
                "departments searching timer").tags(List.of(titleTag)).register(meterRegistry));

        return result;
    }

}

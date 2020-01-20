package ua.edu.sumdu.j2se.dumanskiy.tasks.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Cloneable, Serializable {
    private String title;
    private boolean active;
    private LocalDateTime time;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int repeatInterval;

    public Task() {
        super();
    }

    public Task(String title, LocalDateTime time) {
        if (time == null) {
            throw new IllegalArgumentException();
        }
        this.title = title;
        this.time = time;
        this.active = true;
    }

    public Task(String title, LocalDateTime start, LocalDateTime end,
         int interval) {
        this.title = title;
        this.startTime = start;
        this.endTime = end;
        this.repeatInterval = interval;
        active = true;
    }
    public Task(String title, LocalDateTime start, LocalDateTime end,
                int interval, boolean active) {
        this.title = title;
        this.startTime = start;
        this.endTime = end;
        this.repeatInterval = interval;
        this.active = active;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getTime() {
        if (time == null) {
            return startTime;
        }
        if (time.isEqual(LocalDateTime.now())) {
            return startTime;
        }
        return time;
    }

    public void setTime(LocalDateTime time) {
            startTime = null;
            endTime = null;
            repeatInterval = 0;
        this.time = time;
    }

    public LocalDateTime getStartTime() {
        if (!isRepeated()) {
            return time;
        }
        return startTime;
    }

    public LocalDateTime getEndTime() {
        if (!isRepeated()) {
            return time;
        }
        return endTime;
    }

    public int getRepeatInterval() {
        if (!isRepeated()) {
            return 0;
        }
        return repeatInterval;
    }

    public void setTime(LocalDateTime start, LocalDateTime end, int interval) {
        time = null;
        startTime = start;
        endTime = end;
        repeatInterval = interval;
    }

    public void setStartTime(LocalDateTime startTime) {
        if (isRepeated()) {
            this.startTime = startTime;
        }
    }

    public void setEndTime(LocalDateTime endTime) {
        if (isRepeated()) {
            this.endTime = endTime;
        }
    }

    public void setRepeatInterval(int repeatInterval) {
        if (isRepeated()) {
            this.repeatInterval = repeatInterval;
        }
    }
    public boolean isRepeated() {
        return startTime != null;
    }
    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        if (!active) {
            return null;
        }
        if (!isRepeated()) {
            if (time.isAfter(current)) {
                return time;
            } else {
                return null;
            }
        } else {
            if (endTime.isBefore(current)) {
                return null;
            } else {
                for (LocalDateTime i = startTime;;
                     i = i.plusMinutes(repeatInterval)) {
                    if (i.isAfter(current) && (i.isBefore(endTime)
                            || i.isEqual(endTime))) {
                        return i;
                    } else if (i.isAfter(endTime)) {
                        return null;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Task{"
                + "title='" + title + '\''
                + ", active=" + active
                + ", time=" + time
                + ", startTime=" + startTime
                + ", endTime=" + endTime
                + ", repeatInterval=" + repeatInterval + '}';
    }

    @Override
    public Task clone() {
        try {
            return (Task) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return active == task.active
                && time == task.time
                && startTime == task.startTime
                && endTime == task.endTime
                && repeatInterval == task.repeatInterval
                && Objects.equals(title, task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, active, time,
                startTime, endTime, repeatInterval);
    }
}

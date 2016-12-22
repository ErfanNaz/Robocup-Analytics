package de.zentoo.robocupanalytics.entity;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Erfan Nazmehr on 06.07.15.
 */
public class PlayerType {

    @DatabaseField(unique = true)
    private int id;

    @DatabaseField private double player_speed_max;
    @DatabaseField private double stamina_inc_max;
    @DatabaseField private double player_decay;
    @DatabaseField private double inertia_moment;
    @DatabaseField private double dash_power_rate;
    @DatabaseField private double player_size;
    @DatabaseField private double kickable_margin;
    @DatabaseField private double kick_rand;
    @DatabaseField private double extra_stamina;
    @DatabaseField private double effort_max;
    @DatabaseField private double effort_min;
    @DatabaseField private double kick_power_rate;
    @DatabaseField private double foul_detect_probability;
    @DatabaseField private double catchable_area_l_stretch;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPlayer_speed_max() {
        return player_speed_max;
    }

    public void setPlayer_speed_max(double player_speed_max) {
        this.player_speed_max = player_speed_max;
    }

    public double getStamina_inc_max() {
        return stamina_inc_max;
    }

    public void setStamina_inc_max(double stamina_inc_max) {
        this.stamina_inc_max = stamina_inc_max;
    }

    public double getPlayer_decay() {
        return player_decay;
    }

    public void setPlayer_decay(double player_decay) {
        this.player_decay = player_decay;
    }

    public double getInertia_moment() {
        return inertia_moment;
    }

    public void setInertia_moment(double inertia_moment) {
        this.inertia_moment = inertia_moment;
    }

    public double getDash_power_rate() {
        return dash_power_rate;
    }

    public void setDash_power_rate(double dash_power_rate) {
        this.dash_power_rate = dash_power_rate;
    }

    public double getPlayer_size() {
        return player_size;
    }

    public void setPlayer_size(double player_size) {
        this.player_size = player_size;
    }

    public double getKickable_margin() {
        return kickable_margin;
    }

    public void setKickable_margin(double kickable_margin) {
        this.kickable_margin = kickable_margin;
    }

    public double getKick_rand() {
        return kick_rand;
    }

    public void setKick_rand(double kick_rand) {
        this.kick_rand = kick_rand;
    }

    public double getExtra_stamina() {
        return extra_stamina;
    }

    public void setExtra_stamina(double extra_stamina) {
        this.extra_stamina = extra_stamina;
    }

    public double getEffort_max() {
        return effort_max;
    }

    public void setEffort_max(double effort_max) {
        this.effort_max = effort_max;
    }

    public double getEffort_min() {
        return effort_min;
    }

    public void setEffort_min(double effort_min) {
        this.effort_min = effort_min;
    }

    public double getKick_power_rate() {
        return kick_power_rate;
    }

    public void setKick_power_rate(double kick_power_rate) {
        this.kick_power_rate = kick_power_rate;
    }

    public double getFoul_detect_probability() {
        return foul_detect_probability;
    }

    public void setFoul_detect_probability(double foul_detect_probability) {
        this.foul_detect_probability = foul_detect_probability;
    }

    public double getCatchable_area_l_stretch() {
        return catchable_area_l_stretch;
    }

    public void setCatchable_area_l_stretch(double catchable_area_l_stretch) {
        this.catchable_area_l_stretch = catchable_area_l_stretch;
    }
}

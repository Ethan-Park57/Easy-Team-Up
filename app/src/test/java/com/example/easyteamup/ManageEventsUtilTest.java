package com.example.easyteamup;

import static org.junit.Assert.*;

import com.example.easyteamup.manage.ManageEventsUtil;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class ManageEventsUtilTest {

    @Test
    public void hasValidID() {
        assertTrue(ManageEventsUtil.hasValidID("1234346534"));
    }

    @Test
    public void hasNotValidID() {
        assertFalse(ManageEventsUtil.hasValidID(""));
    }

    @Test
    public void hasValidName() {
        assertTrue(ManageEventsUtil.hasValidName("Study Sesh"));
    }

    @Test
    public void hasNotValidName() {
        assertFalse(ManageEventsUtil.hasValidName(""));
    }

    @Test
    public void hasValidLocation() {
        assertTrue(ManageEventsUtil.hasValidLocation("Leavey Library"));
    }

    @Test
    public void hasNotValidLocation() {
        assertFalse(ManageEventsUtil.hasValidLocation(""));
    }

    @Test
    public void hasValidDescription() {
        assertTrue(ManageEventsUtil.hasValidDescription("Fun Study Sesh!"));
    }

    @Test
    public void hasNotValidDescription() {
        assertFalse(ManageEventsUtil.hasValidDescription(""));
    }

    @Test
    public void hasValidDueTime() {
        assertTrue(ManageEventsUtil.hasValidDueTime("December 31, 2022"));
    }

    @Test
    public void hasNotValidDueTime() {
        assertFalse(ManageEventsUtil.hasValidDueTime(""));
    }
}
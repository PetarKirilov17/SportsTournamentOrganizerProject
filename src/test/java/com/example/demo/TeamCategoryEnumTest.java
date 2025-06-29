package com.example.demo;

import com.example.demo.enums.TeamCategoryEnum;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TeamCategoryEnumTest {

    @Test
    public void testCaseInsensitiveDeserialization() {
        // Test exact match
        assertEquals(TeamCategoryEnum.AMATEUR, TeamCategoryEnum.fromString("AMATEUR"));
        assertEquals(TeamCategoryEnum.PROFESSIONAL, TeamCategoryEnum.fromString("PROFESSIONAL"));
        assertEquals(TeamCategoryEnum.YOUTH, TeamCategoryEnum.fromString("YOUTH"));

        // Test lowercase
        assertEquals(TeamCategoryEnum.AMATEUR, TeamCategoryEnum.fromString("amateur"));
        assertEquals(TeamCategoryEnum.PROFESSIONAL, TeamCategoryEnum.fromString("professional"));
        assertEquals(TeamCategoryEnum.YOUTH, TeamCategoryEnum.fromString("youth"));

        // Test mixed case
        assertEquals(TeamCategoryEnum.AMATEUR, TeamCategoryEnum.fromString("Amateur"));
        assertEquals(TeamCategoryEnum.PROFESSIONAL, TeamCategoryEnum.fromString("Professional"));
        assertEquals(TeamCategoryEnum.YOUTH, TeamCategoryEnum.fromString("Youth"));

        // Test description match
        assertEquals(TeamCategoryEnum.AMATEUR, TeamCategoryEnum.fromString("Amateur"));
        assertEquals(TeamCategoryEnum.PROFESSIONAL, TeamCategoryEnum.fromString("Professional"));
        assertEquals(TeamCategoryEnum.YOUTH, TeamCategoryEnum.fromString("Youth"));
    }

    @Test
    public void testInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            TeamCategoryEnum.fromString("INVALID");
        });
    }

    @Test
    public void testNullValue() {
        assertNull(TeamCategoryEnum.fromString(null));
    }
} 
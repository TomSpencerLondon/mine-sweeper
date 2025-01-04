package org.example.adapter.out.console;

import org.example.hexagon.domain.Coordinate;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CoordinatorValidatorTest {
    @Test
    void coordinateWithZeroColumnShouldReturnFalse() {
        CoordinateValidator coordinateValidator = new CoordinateValidator(9, 9);
        Coordinate coordinate = new Coordinate(4, 0);
        assertThat(coordinateValidator.isValid(coordinate))
                .isFalse();
    }
}

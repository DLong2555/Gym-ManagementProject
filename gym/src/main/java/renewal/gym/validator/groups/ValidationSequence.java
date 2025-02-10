package renewal.gym.validator.groups;

import jakarta.validation.GroupSequence;

import static renewal.gym.validator.groups.ValidationGroups.*;

@GroupSequence({NotBlankGroup.class, PatternGroup.class})
public interface ValidationSequence {
}

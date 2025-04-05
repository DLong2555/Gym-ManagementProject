package renewal.gym.validator.groups;

import jakarta.validation.GroupSequence;

import static renewal.gym.validator.groups.ValidationGroups.*;

@GroupSequence({NotEmptyGroup.class, PatternGroup.class, MinGroup.class, MaxGroup.class})
public interface ValidationSequence {
}

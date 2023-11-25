package main.model.camp;

import main.utils.exception.ModelNotFoundException;

import java.util.List;

public interface DisplayCampsEnrolled {
    public List<Integer> displayCampsEnrolled() throws ModelNotFoundException;
}

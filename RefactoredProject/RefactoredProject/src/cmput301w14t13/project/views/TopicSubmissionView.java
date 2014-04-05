package cmput301w14t13.project.views;

import android.os.Bundle;
import cmput301w14t13.project.auxilliary.interfaces.RankedHierarchicalActivity;
import cmput301w14t13.project.auxilliary.interfaces.UpdateInterface;
import cmput301w14t13.project.controllers.CreateSubmissionController;
import cmput301w14t13.project.controllers.TopicSubmissionController;

public abstract class TopicSubmissionView extends RankedHierarchicalActivity implements UpdateInterface {

	protected TopicSubmissionController controller;

}

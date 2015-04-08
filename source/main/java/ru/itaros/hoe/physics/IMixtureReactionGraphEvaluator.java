package ru.itaros.hoe.physics;

import ru.itaros.hoe.itemhandling.MixtureStack;

public interface IMixtureReactionGraphEvaluator {

	void evaluateAgainstMixture(MixtureStack mixture,
			MixtureReactionFramework framework);

}

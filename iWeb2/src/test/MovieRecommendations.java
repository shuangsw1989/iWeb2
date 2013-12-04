package test;

import org.yooreeka.algos.reco.collab.data.MovieLensData;
import org.yooreeka.algos.reco.collab.data.MovieLensDataset;
import org.yooreeka.algos.reco.collab.recommender.MovieLensDelphi;

public class MovieRecommendations {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Create the dataset
		MovieLensDataset ds = MovieLensData.createDataset();

		// Create the recommender
		MovieLensDelphi delphi = new MovieLensDelphi(ds);

		// Pick users and create recommendations
		org.yooreeka.algos.reco.collab.model.User u1 = ds.getUser(1);
		delphi.recommend(u1);

		org.yooreeka.algos.reco.collab.model.User u155 = ds.getUser(155);
		delphi.recommend(u155);

		org.yooreeka.algos.reco.collab.model.User u876 = ds.getUser(876);
		delphi.recommend(u876);
	}

}

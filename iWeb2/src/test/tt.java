package test;

import org.yooreeka.algos.reco.collab.data.BaseDataset;
import org.yooreeka.algos.reco.collab.data.MusicItem;
import org.yooreeka.algos.reco.collab.data.MusicUser;
import org.yooreeka.algos.reco.collab.model.RecommendationType;
import org.yooreeka.algos.reco.collab.recommender.Delphi;
import org.yooreeka.config.YooreekaConfigurator;


public class tt {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String yHome = YooreekaConfigurator.getHome();

		//
		// Load the dataset that we created before
		//
		BaseDataset ds = BaseDataset.load(yHome+"/data/ch03/dataset_script_2.ser");

		//
		// use Delphi with ITEM_BASED similarity
		//
		Delphi delphi = new Delphi(ds,RecommendationType.ITEM_BASED);
		delphi.setVerbose(true);

		//
		// Show me recommendations for user X (top 5)
		//
		MusicUser mu1 = (MusicUser)ds.pickUser("Bob");
		delphi.recommend(mu1);

		//
		// Show me items like X (top 5)
		//
		MusicItem mi = (MusicItem)ds.pickItem("La Bamba");
		delphi.findSimilarItems(mi);

	}

}

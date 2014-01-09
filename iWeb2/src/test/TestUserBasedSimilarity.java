package test;

import org.yooreeka.algos.reco.collab.data.BaseDataset;
import org.yooreeka.algos.reco.collab.data.MusicData;
import org.yooreeka.algos.reco.collab.data.MusicUser;
import org.yooreeka.algos.reco.collab.model.RecommendationType;
import org.yooreeka.algos.reco.collab.recommender.Delphi;
import org.yooreeka.config.YooreekaConfigurator;

public class TestUserBasedSimilarity {
	public static void main(String[] args) {
		
	
	// ------------------------------------------------------
//  Recommendations based on user similarity 
//------------------------------------------------------

String yHome = YooreekaConfigurator.getHome();

//The static method createDataset will create random ratings
//for all the users, pick 75% of all the songs and assign ratings.
//
//Users whose name starts from A to D should have ratings between 3 and 5
//Users whose name starts from E to Z should have ratings between 1 and 3
//
BaseDataset ds = MusicData.createDataset();

//
//Serialize the dataset, so that we can load it in the next script
//

ds.save(yHome+"/data/ch03/dataset_script_2.ser");

//
//use Delphi with USER_BASED similarity
//
Delphi delphi = new Delphi(ds,RecommendationType.USER_BASED);
delphi.setVerbose(true);

//
//Show me users like X (top 5)
//
MusicUser mu1 = (MusicUser)ds.pickUser("Bob");
delphi.findSimilarUsers(mu1);

MusicUser mu2 = (MusicUser)ds.pickUser("John");
delphi.findSimilarUsers(mu2);

//
//Show me recommendations for user X (top 5)
//
delphi.recommend(mu1);

//------------------------------------------------------------------------
//BaseDataset ds = MusicData.createDataset();//创建音乐数据集
//ds.save(yHome+"/data/ch03/dataset_script_2.ser");//保存它以备后用
//Delphi delphi = new Delphi(ds,RecommendationType.USER_BASED);//创建推荐引擎

//delphi.setVerbose(true);//设置记录模式
//找到相似用户
//MusicUser mu1 = ds.pickUser("Bob");
//delphi.findSimilarUsers(mu1);
//MusicUser mu2 = ds.pickUser("John");
//delphi.findSimilarUsers(mu2);
//delphi.recommend(mu1);//推荐一些歌曲
//------------------------------------------------------------------------

}
}

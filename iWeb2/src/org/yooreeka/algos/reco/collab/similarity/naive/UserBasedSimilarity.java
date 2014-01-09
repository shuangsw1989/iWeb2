/*
 *   ________________________________________________________________________________________
 *   
 *   Y O O R E E K A
 *   A library for data mining, machine learning, soft computing, and mathematical analysis
 *   ________________________________________________________________________________________ 
 *    
 *   The Yooreeka project started with the code of the book "Algorithms of the Intelligent Web " 
 *   (Manning 2009). Although the term "Web" prevailed in the title, in essence, the algorithms 
 *   are valuable in any software application.
 *  
 *   Copyright (c) 2007-2009 Haralambos Marmanis & Dmitry Babenko
 *   Copyright (c) 2009-${year} Marmanis Group LLC and individual contributors as indicated by the @author tags.  
 * 
 *   Certain library functions depend on other Open Source software libraries, which are covered 
 *   by different license agreements. See the NOTICE file distributed with this work for additional 
 *   information regarding copyright ownership and licensing.
 * 
 *   Marmanis Group LLC licenses this file to You under the Apache License, Version 2.0 (the "License"); 
 *   you may not use this file except in compliance with the License.  
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software distributed under 
 *   the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 *   either express or implied. See the License for the specific language governing permissions and
 *   limitations under the License.
 *   
 */
package org.yooreeka.algos.reco.collab.similarity.naive;

import org.yooreeka.algos.reco.collab.model.Dataset;
import org.yooreeka.algos.reco.collab.model.User;
import org.yooreeka.algos.reco.collab.similarity.util.RatingCountMatrix;

public class UserBasedSimilarity extends SimilarityMatrixImpl {

	/**
	 * Unique identifier for serialization
	 */
	private static final long serialVersionUID = 5741616253320567238L;

	public UserBasedSimilarity(Dataset dataSet) {

		this(UserBasedSimilarity.class.getSimpleName(), dataSet, true);
	}

	public UserBasedSimilarity(String id, Dataset dataSet,
			boolean keepRatingCountMatrix) {
		this.id = id;
		this.keepRatingCountMatrix = keepRatingCountMatrix;
		this.useObjIdToIndexMapping = dataSet.isIdMappingRequired();
		calculate(dataSet);
	}

	// here we assume that userId and bookId are:
	// - integers,
	// - start with 1
	// - have no gaps in sequence.
	// Otherwise we would have to have a mapping from userId/bookId into index
	@Override
	protected void calculate(Dataset dataSet) {

		int nUsers = dataSet.getUserCount();//定义相似度矩阵的规模
		int nRatingValues = 5;//定义评分计数矩阵的规模

		similarityValues = new double[nUsers][nUsers];

		if (keepRatingCountMatrix) {
			ratingCountMatrix = new RatingCountMatrix[nUsers][nUsers];
		}

		// 如果需要从用户Id到下标索引的映射，则为每个用户生成索引
		if (useObjIdToIndexMapping) {
			for (User u : dataSet.getUsers()) {
				idMapping.getIndex(String.valueOf(u.getId()));
			}
		}

		for (int u = 0; u < nUsers; u++) {

			int userAId = getObjIdFromIndex(u);
			User userA = dataSet.getUser(userAId);

			for (int v = u + 1; v < nUsers; v++) {//相似度矩阵

				int userBId = getObjIdFromIndex(v);
				User userB = dataSet.getUser(userBId);

				RatingCountMatrix rcm = new RatingCountMatrix(userA, userB,
						nRatingValues);//两个用户评分的一般性矩阵

				int totalCount = rcm.getTotalCount();
				int agreementCount = rcm.getAgreementCount();

				if (agreementCount > 0) {//计算相似度或把它置为0

					similarityValues[u][v] = (double) agreementCount
							/ (double) totalCount;
				} else {
					similarityValues[u][v] = 0.0;
				}

				// 对大数据集的处理
				if (keepRatingCountMatrix) {
					ratingCountMatrix[u][v] = rcm;
				}
			}

			// 对于u==v的情况，赋值为1
			// 此时不会创建RatingCountMatrix
			similarityValues[u][u] = 1.0;//相似度矩阵
		}
	}
}

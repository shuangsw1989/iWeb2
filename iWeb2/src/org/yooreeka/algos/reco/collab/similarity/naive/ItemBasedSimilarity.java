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
import org.yooreeka.algos.reco.collab.model.Item;
import org.yooreeka.algos.reco.collab.similarity.util.RatingCountMatrix;

public class ItemBasedSimilarity extends SimilarityMatrixImpl {

	/**
     * 
     */
	private static final long serialVersionUID = 3062035062791168163L;

	public ItemBasedSimilarity(String id, Dataset dataSet,
			boolean keepRatingCountMatrix) {
		this.id = id;
		this.keepRatingCountMatrix = keepRatingCountMatrix;
		this.useObjIdToIndexMapping = dataSet.isIdMappingRequired();
		calculate(dataSet);
	}

	@Override
	protected void calculate(Dataset dataSet) {

		int nItems = dataSet.getItemCount();
		int nRatingValues = 5;

		similarityValues = new double[nItems][nItems];

		if (keepRatingCountMatrix) {
			ratingCountMatrix = new RatingCountMatrix[nItems][nItems];
		}

		// if we want to use mapping from itemId to index then generate
		// index for every itemId
		if (useObjIdToIndexMapping) {
			for (Item item : dataSet.getItems()) {
				idMapping.getIndex(String.valueOf(item.getId()));
			}
		}

		int totalCount = 0;
		int agreementCount = 0;

		for (int u = 0; u < nItems; u++) {

			int itemAId = getObjIdFromIndex(u);
			Item itemA = dataSet.getItem(itemAId);

			// we only need to calculate elements above the main diagonal.
			for (int v = u + 1; v < nItems; v++) {

				int itemBId = getObjIdFromIndex(v);

				Item itemB = dataSet.getItem(itemBId);

				RatingCountMatrix rcm = new RatingCountMatrix(itemA, itemB,
						nRatingValues);

				totalCount = rcm.getTotalCount();
				agreementCount = rcm.getAgreementCount();

				if (agreementCount > 0) {
					similarityValues[u][v] = (double) agreementCount
							/ (double) totalCount;
				} else {
					similarityValues[u][v] = 0.0;
				}

				if (keepRatingCountMatrix) {
					ratingCountMatrix[u][v] = rcm;
				}
			}

			// for u == v assign 1
			similarityValues[u][u] = 1.0;

		}
	}
}

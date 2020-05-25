# stock-signaller
Stock signaller sends signals of certain stock positions sorted by a simple algorithm based on 
a mix of factors.

Factors:
	* Mix of current position of RSI, Full stocastic. (TBD)
	* Current trend of the operational market. (TBD)
	* Trend of positions of the previous RSI and stocastic. (TBD)

Features:

	* Set up logging (TBD)
	* Cache Infinispan integration and use thru CDI stereotypes.(TBD)
	* UI of stock management (TBD).
	* Bulk update of stock management (Based on a CSV file, it will process the whole batch and return those wrong/failing entries, multithreading)(TBD).
	* Integration to different technical indicator feeding (TBD).
	* Algorithm to calculate the price expectation (TBD).
	* Storage of price expectation results (TBD).
	* UI of price expectation results (TBD).
	* Batch job timing for feeding the indicators on a regular basis (the idea is to do it every three days) (TBD).
	* Threading and synchronizing feeding jobs based on the API plan used (TBD).
	* Cleaning job of the database based on the MongoDB plan (Basically past indicators may be aggregated in future calculations, let's see how it's going the size scaling of the database during the first tests and decide) (TBD).
	* Integration to send email reporting (when new resolution avaiable, new resolution report on PDF) (TBD)
	

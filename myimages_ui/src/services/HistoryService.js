import http from "../http-common"; 

class HistoryService {
  getAllHistorys(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/history/historys`, searchDTO);
  }
 

  get(historyId) {
    return this.getRequest(`/history/${historyId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/history?field=${matchData}`, null);
  }

  addHistory(data) {
    return http.post("/history/addHistory", data);
  }

  update(data) {
  	return http.post("/history/updateHistory", data);
  }
  
  uploadImage(data,historyId) {
  	return http.postForm("/history/uploadImage/"+historyId, data);
  }




	postRequest(url, data) {
		return http.post(url, data);
      };

	getRequest(url, params) {
        return http.get(url, {
        	params: params
        });
    };

}

export default new HistoryService();

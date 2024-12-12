import http from "../http-common"; 

class PromptConfigService {
  getAllPromptConfigs(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/promptConfig/promptConfigs`, searchDTO);
  }
 

  get(promptConfigId) {
    return this.getRequest(`/promptConfig/${promptConfigId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/promptConfig?field=${matchData}`, null);
  }

  addPromptConfig(data) {
    return http.post("/promptConfig/addPromptConfig", data);
  }

  update(data) {
  	return http.post("/promptConfig/updatePromptConfig", data);
  }
  
  uploadImage(data,promptConfigId) {
  	return http.postForm("/promptConfig/uploadImage/"+promptConfigId, data);
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

export default new PromptConfigService();

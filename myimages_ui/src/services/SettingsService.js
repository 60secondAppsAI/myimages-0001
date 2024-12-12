import http from "../http-common"; 

class SettingsService {
  getAllSettingss(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/settings/settingss`, searchDTO);
  }
 

  get(settingsId) {
    return this.getRequest(`/settings/${settingsId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/settings?field=${matchData}`, null);
  }

  addSettings(data) {
    return http.post("/settings/addSettings", data);
  }

  update(data) {
  	return http.post("/settings/updateSettings", data);
  }
  
  uploadImage(data,settingsId) {
  	return http.postForm("/settings/uploadImage/"+settingsId, data);
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

export default new SettingsService();

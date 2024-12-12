import http from "../http-common"; 

class UserProfileService {
  getAllUserProfiles(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/userProfile/userProfiles`, searchDTO);
  }
 

  get(userProfileId) {
    return this.getRequest(`/userProfile/${userProfileId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/userProfile?field=${matchData}`, null);
  }

  addUserProfile(data) {
    return http.post("/userProfile/addUserProfile", data);
  }

  update(data) {
  	return http.post("/userProfile/updateUserProfile", data);
  }
  
  uploadImage(data,userProfileId) {
  	return http.postForm("/userProfile/uploadImage/"+userProfileId, data);
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

export default new UserProfileService();

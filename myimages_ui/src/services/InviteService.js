import http from "../http-common"; 

class InviteService {
  getAllInvites(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/invite/invites`, searchDTO);
  }
 

  get(inviteId) {
    return this.getRequest(`/invite/${inviteId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/invite?field=${matchData}`, null);
  }

  addInvite(data) {
    return http.post("/invite/addInvite", data);
  }

  update(data) {
  	return http.post("/invite/updateInvite", data);
  }
  
  uploadImage(data,inviteId) {
  	return http.postForm("/invite/uploadImage/"+inviteId, data);
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

export default new InviteService();

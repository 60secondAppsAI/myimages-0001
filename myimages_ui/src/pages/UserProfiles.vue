<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <card class="card-plain">
          <!-- <template slot="header">
            <h4 class="card-title">Table on Plain Background</h4>
            <p class="category">Here is a subtitle for this table</p>
          </template>-->
          <div class="table-full-width text-left">
            <userProfile-table
            v-if="userProfiles && userProfiles.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:userProfiles="userProfiles"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-user-profiles="getAllUserProfiles"
             >

            </userProfile-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import UserProfileTable from "@/components/UserProfileTable";
import UserProfileService from "../services/UserProfileService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    UserProfileTable,
  },
  data() {
    return {
      userProfiles: [],
	  totalElements: 0,
      page: 0,
      searchQuery: '',     
      table1: {
        title: "Simple Table",
        columns: [...tableColumns],
        data: [...tableData],
      },
    };
  },
  methods: {
    async getAllUserProfiles(sortBy='userProfileId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await UserProfileService.getAllUserProfiles(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.userProfiles.length) {
					this.userProfiles = response.data.userProfiles;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching userProfiles:", error);
        }
        
      } catch (error) {
        console.error("Error fetching userProfile details:", error);
      }
    },
  },
  mounted() {
    this.getAllUserProfiles();
  },
  created() {
    this.$root.$on('searchQueryForUserProfilesChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllUserProfiles();
    })
  }
};
</script>
<style></style>

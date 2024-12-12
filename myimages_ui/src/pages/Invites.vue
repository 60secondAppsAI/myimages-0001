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
            <invite-table
            v-if="invites && invites.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:invites="invites"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-invites="getAllInvites"
             >

            </invite-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import InviteTable from "@/components/InviteTable";
import InviteService from "../services/InviteService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    InviteTable,
  },
  data() {
    return {
      invites: [],
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
    async getAllInvites(sortBy='inviteId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await InviteService.getAllInvites(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.invites.length) {
					this.invites = response.data.invites;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching invites:", error);
        }
        
      } catch (error) {
        console.error("Error fetching invite details:", error);
      }
    },
  },
  mounted() {
    this.getAllInvites();
  },
  created() {
    this.$root.$on('searchQueryForInvitesChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllInvites();
    })
  }
};
</script>
<style></style>

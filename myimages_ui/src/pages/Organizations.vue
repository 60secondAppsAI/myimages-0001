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
            <organization-table
            v-if="organizations && organizations.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:organizations="organizations"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-organizations="getAllOrganizations"
             >

            </organization-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import OrganizationTable from "@/components/OrganizationTable";
import OrganizationService from "../services/OrganizationService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    OrganizationTable,
  },
  data() {
    return {
      organizations: [],
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
    async getAllOrganizations(sortBy='organizationId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await OrganizationService.getAllOrganizations(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.organizations.length) {
					this.organizations = response.data.organizations;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching organizations:", error);
        }
        
      } catch (error) {
        console.error("Error fetching organization details:", error);
      }
    },
  },
  mounted() {
    this.getAllOrganizations();
  },
  created() {
    this.$root.$on('searchQueryForOrganizationsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllOrganizations();
    })
  }
};
</script>
<style></style>

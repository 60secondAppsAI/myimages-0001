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
            <license-table
            v-if="licenses && licenses.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:licenses="licenses"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-licenses="getAllLicenses"
             >

            </license-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import LicenseTable from "@/components/LicenseTable";
import LicenseService from "../services/LicenseService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    LicenseTable,
  },
  data() {
    return {
      licenses: [],
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
    async getAllLicenses(sortBy='licenseId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await LicenseService.getAllLicenses(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.licenses.length) {
					this.licenses = response.data.licenses;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching licenses:", error);
        }
        
      } catch (error) {
        console.error("Error fetching license details:", error);
      }
    },
  },
  mounted() {
    this.getAllLicenses();
  },
  created() {
    this.$root.$on('searchQueryForLicensesChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllLicenses();
    })
  }
};
</script>
<style></style>

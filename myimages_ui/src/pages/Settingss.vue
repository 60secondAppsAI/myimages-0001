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
            <settings-table
            v-if="settingss && settingss.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:settingss="settingss"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-settingss="getAllSettingss"
             >

            </settings-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import SettingsTable from "@/components/SettingsTable";
import SettingsService from "../services/SettingsService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    SettingsTable,
  },
  data() {
    return {
      settingss: [],
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
    async getAllSettingss(sortBy='settingsId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await SettingsService.getAllSettingss(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.settingss.length) {
					this.settingss = response.data.settingss;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching settingss:", error);
        }
        
      } catch (error) {
        console.error("Error fetching settings details:", error);
      }
    },
  },
  mounted() {
    this.getAllSettingss();
  },
  created() {
    this.$root.$on('searchQueryForSettingssChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllSettingss();
    })
  }
};
</script>
<style></style>

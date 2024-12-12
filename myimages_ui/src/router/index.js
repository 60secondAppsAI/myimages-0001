import Vue from "vue";
import VueRouter from "vue-router";
import DefaultLayout from "@/layouts/DefaultLayout.vue";
import PromptConfigs from  '@/pages/PromptConfigs.vue';
import PromptConfigDetail from  '@/pages/PromptConfigDetail.vue';
import ImageRequests from  '@/pages/ImageRequests.vue';
import ImageRequestDetail from  '@/pages/ImageRequestDetail.vue';
import UserProfiles from  '@/pages/UserProfiles.vue';
import UserProfileDetail from  '@/pages/UserProfileDetail.vue';
import Images from  '@/pages/Images.vue';
import ImageDetail from  '@/pages/ImageDetail.vue';
import Gallerys from  '@/pages/Gallerys.vue';
import GalleryDetail from  '@/pages/GalleryDetail.vue';
import GalleryImages from  '@/pages/GalleryImages.vue';
import GalleryImageDetail from  '@/pages/GalleryImageDetail.vue';
import Tags from  '@/pages/Tags.vue';
import TagDetail from  '@/pages/TagDetail.vue';
import ImageTags from  '@/pages/ImageTags.vue';
import ImageTagDetail from  '@/pages/ImageTagDetail.vue';
import Feedbacks from  '@/pages/Feedbacks.vue';
import FeedbackDetail from  '@/pages/FeedbackDetail.vue';
import ImageMetadatas from  '@/pages/ImageMetadatas.vue';
import ImageMetadataDetail from  '@/pages/ImageMetadataDetail.vue';
import Historys from  '@/pages/Historys.vue';
import HistoryDetail from  '@/pages/HistoryDetail.vue';
import Settingss from  '@/pages/Settingss.vue';
import SettingsDetail from  '@/pages/SettingsDetail.vue';
import Subscriptions from  '@/pages/Subscriptions.vue';
import SubscriptionDetail from  '@/pages/SubscriptionDetail.vue';
import Payments from  '@/pages/Payments.vue';
import PaymentDetail from  '@/pages/PaymentDetail.vue';
import Organizations from  '@/pages/Organizations.vue';
import OrganizationDetail from  '@/pages/OrganizationDetail.vue';
import UserRoles from  '@/pages/UserRoles.vue';
import UserRoleDetail from  '@/pages/UserRoleDetail.vue';
import Licenses from  '@/pages/Licenses.vue';
import LicenseDetail from  '@/pages/LicenseDetail.vue';
import Notifications from  '@/pages/Notifications.vue';
import NotificationDetail from  '@/pages/NotificationDetail.vue';
import Events from  '@/pages/Events.vue';
import EventDetail from  '@/pages/EventDetail.vue';
import Invites from  '@/pages/Invites.vue';
import InviteDetail from  '@/pages/InviteDetail.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: () => import("../views/HomeView.vue"),
			redirect: '/promptConfigs',
																				  },
 


	{
		path: '/promptConfigs',
		name: 'PromptConfigs',
		layout: DefaultLayout,
		component: PromptConfigs,
	},
	{
	    path: '/promptConfig/:promptConfigId', 
	    name: 'PromptConfigDetail',
		layout: DefaultLayout,
	    component: PromptConfigDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/imageRequests',
		name: 'ImageRequests',
		layout: DefaultLayout,
		component: ImageRequests,
	},
	{
	    path: '/imageRequest/:imageRequestId', 
	    name: 'ImageRequestDetail',
		layout: DefaultLayout,
	    component: ImageRequestDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/userProfiles',
		name: 'UserProfiles',
		layout: DefaultLayout,
		component: UserProfiles,
	},
	{
	    path: '/userProfile/:userProfileId', 
	    name: 'UserProfileDetail',
		layout: DefaultLayout,
	    component: UserProfileDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/images',
		name: 'Images',
		layout: DefaultLayout,
		component: Images,
	},
	{
	    path: '/image/:imageId', 
	    name: 'ImageDetail',
		layout: DefaultLayout,
	    component: ImageDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/gallerys',
		name: 'Gallerys',
		layout: DefaultLayout,
		component: Gallerys,
	},
	{
	    path: '/gallery/:galleryId', 
	    name: 'GalleryDetail',
		layout: DefaultLayout,
	    component: GalleryDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/galleryImages',
		name: 'GalleryImages',
		layout: DefaultLayout,
		component: GalleryImages,
	},
	{
	    path: '/galleryImage/:galleryImageId', 
	    name: 'GalleryImageDetail',
		layout: DefaultLayout,
	    component: GalleryImageDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/tags',
		name: 'Tags',
		layout: DefaultLayout,
		component: Tags,
	},
	{
	    path: '/tag/:tagId', 
	    name: 'TagDetail',
		layout: DefaultLayout,
	    component: TagDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/imageTags',
		name: 'ImageTags',
		layout: DefaultLayout,
		component: ImageTags,
	},
	{
	    path: '/imageTag/:imageTagId', 
	    name: 'ImageTagDetail',
		layout: DefaultLayout,
	    component: ImageTagDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/feedbacks',
		name: 'Feedbacks',
		layout: DefaultLayout,
		component: Feedbacks,
	},
	{
	    path: '/feedback/:feedbackId', 
	    name: 'FeedbackDetail',
		layout: DefaultLayout,
	    component: FeedbackDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/imageMetadatas',
		name: 'ImageMetadatas',
		layout: DefaultLayout,
		component: ImageMetadatas,
	},
	{
	    path: '/imageMetadata/:imageMetadataId', 
	    name: 'ImageMetadataDetail',
		layout: DefaultLayout,
	    component: ImageMetadataDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/historys',
		name: 'Historys',
		layout: DefaultLayout,
		component: Historys,
	},
	{
	    path: '/history/:historyId', 
	    name: 'HistoryDetail',
		layout: DefaultLayout,
	    component: HistoryDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/settingss',
		name: 'Settingss',
		layout: DefaultLayout,
		component: Settingss,
	},
	{
	    path: '/settings/:settingsId', 
	    name: 'SettingsDetail',
		layout: DefaultLayout,
	    component: SettingsDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/subscriptions',
		name: 'Subscriptions',
		layout: DefaultLayout,
		component: Subscriptions,
	},
	{
	    path: '/subscription/:subscriptionId', 
	    name: 'SubscriptionDetail',
		layout: DefaultLayout,
	    component: SubscriptionDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/payments',
		name: 'Payments',
		layout: DefaultLayout,
		component: Payments,
	},
	{
	    path: '/payment/:paymentId', 
	    name: 'PaymentDetail',
		layout: DefaultLayout,
	    component: PaymentDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/organizations',
		name: 'Organizations',
		layout: DefaultLayout,
		component: Organizations,
	},
	{
	    path: '/organization/:organizationId', 
	    name: 'OrganizationDetail',
		layout: DefaultLayout,
	    component: OrganizationDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/userRoles',
		name: 'UserRoles',
		layout: DefaultLayout,
		component: UserRoles,
	},
	{
	    path: '/userRole/:userRoleId', 
	    name: 'UserRoleDetail',
		layout: DefaultLayout,
	    component: UserRoleDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/licenses',
		name: 'Licenses',
		layout: DefaultLayout,
		component: Licenses,
	},
	{
	    path: '/license/:licenseId', 
	    name: 'LicenseDetail',
		layout: DefaultLayout,
	    component: LicenseDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/notifications',
		name: 'Notifications',
		layout: DefaultLayout,
		component: Notifications,
	},
	{
	    path: '/notification/:notificationId', 
	    name: 'NotificationDetail',
		layout: DefaultLayout,
	    component: NotificationDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/events',
		name: 'Events',
		layout: DefaultLayout,
		component: Events,
	},
	{
	    path: '/event/:eventId', 
	    name: 'EventDetail',
		layout: DefaultLayout,
	    component: EventDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/invites',
		name: 'Invites',
		layout: DefaultLayout,
		component: Invites,
	},
	{
	    path: '/invite/:inviteId', 
	    name: 'InviteDetail',
		layout: DefaultLayout,
	    component: InviteDetail,
	    props: true // Pass route params as props to the component
  	},
];

const router = new VueRouter({
  mode: "hash",
  base: process.env.BASE_URL,
  routes,
});

export default router;

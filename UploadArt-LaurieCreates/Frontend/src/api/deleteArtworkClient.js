import BaseClass from "../util/baseClass";
import axios from 'axios'

export default class DeleteArtworkClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'getArtwork', 'deleteArtwork'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     * @param client The client that has been successfully loaded.
     */
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }

    async getArtwork(id, errorCallback) {
        try {
            const response = await this.client.get(`/artwork/${id}`);
            return response.data;
        } catch (error) {
            this.handleError("getArtwork", error, errorCallback)
        }
    }

    /**
     * Gets the concert for the given ID.
     * @param id Unique identifier for a concert
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The concert
     */
     async deleteArtwork(id, errorCallback) {
        try {
            const response = await this.client.delete(`/artwork/${id}`);
//            const response = await this.client.delete(`/artwork`, {
//            id: id
//            });
            return response.data;
        } catch (error) {
            this.handleError("deleteArtwork", error, errorCallback)
        }
     }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(method, error, errorCallback) {
        console.error(method + " failed - " + error);
        if (error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
        if (errorCallback) {
            errorCallback(method + " failed - " + error);
        }
    }
}

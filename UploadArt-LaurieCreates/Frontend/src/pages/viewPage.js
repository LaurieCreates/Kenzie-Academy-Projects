import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ViewArtworkClient from "../api/viewArtworkClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class ViewPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGet', 'onGetAll', 'renderArtwork', 'renderArtworkList'], this);
        this.dataStore = new DataStore();
        this.dataStoreList = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('view-artwork-form').addEventListener('submit', this.onGet);
        document.getElementById('view-all-artwork-form').addEventListener('submit', this.onGetAll);

            this.client = new ViewArtworkClient();

            this.dataStore.addChangeListener(this.renderArtwork);
            this.dataStoreList.addChangeListener(this.renderArtworkList);
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderArtwork() {
    
        let resultArea = document.getElementById("view-artwork-form");

        const artwork = this.dataStore.get("artwork");

        if (artwork) {
            resultArea.innerHTML = `
                <body>
                    <h3 style = "background: -webkit-linear-gradient(#0000FF, #008080,#0000FF)";
                                                         -webkit-background-clip: text;
                                                         -webkit-text-fill-color: transparent;
                                                         "align-text:center";>
                    <label style="text-align: center">
                    Name: ${artwork.artistName}<br>
                    Title: ${artwork.title}<br>
                    DateCreated: ${artwork.dateCreated}<br>
                    Height: ${artwork.height}<br>
                    Width: ${artwork.width}<br>
                    ForSale: ${artwork.forSale}<br>
                    Price: ${artwork.price}<br>
                    ID: ${artwork.id}<br>
                    Date Posted: ${artwork.datePosted}<br>
                    Is Sold: ${artwork.sold}<br></label>
                    </h3>
                   <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                                               <img src="https://i.pinimg.com/originals/03/70/56/037056d846b52719707135af3a79073e.jpg">
            <label style="text-align: center">
                                                <a href="mailto:email@sample.com">CONTACT US</a>
                                                </label>
                    </body>
            `
        } else {
            resultArea.innerHTML = "No Item";
        }
    }

        async renderArtworkList() {

            let resultArea = document.getElementById("view-all-artwork-form");

            const artworks = this.dataStoreList.get("artworks");

            resultArea.innerHTML = ''

            for (const artwork of artworks) {
                resultArea.innerHTML += `
                    <body>
                        <h3 style = "background: -webkit-linear-gradient(#0000FF, #008080,#0000FF)";
                                                             -webkit-background-clip: text;
                                                             -webkit-text-fill-color: transparent;
                                                             "align-text:center";>
                         <label style="text-align: center">
                        Name: ${artwork.artistName}<br>
                        Title: ${artwork.title}<br>
                        DateCreated: ${artwork.dateCreated}<br>
                        Height: ${artwork.height}<br>
                        Width: ${artwork.width}<br>
                        ForSale: ${artwork.forSale}<br>
                        Price: ${artwork.price}<br>
                        ID: ${artwork.id}<br>
                        Date Posted: ${artwork.datePosted}<br>
                        Is Sold: ${artwork.sold}<br>
                        </label>
                        </h3>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                           <img src="https://i.pinimg.com/originals/03/70/56/037056d846b52719707135af3a79073e.jpg">
                       </body>
                `
            }
        }

    // Event Handlers --------------------------------------------------------------------------------------------------
    async onGetAll(event) {
        event.preventDefault();

        const artworks = await this.client.getAllArtwork(this.errorHandler)

        this.dataStoreList.set("artworks", artworks);
    }

    async onGet(event) {
        event.preventDefault();

        let id = document.getElementById("view-artwork-id").value;
        this.dataStore.set("artwork", null);

        let result = await this.client.getArtwork(id, this.errorHandler);
        this.dataStore.set("artwork", result);

        if (result) {
            this.showMessage(`Found artwork ${result.title}!`)
        } else {
            this.errorHandler("No artwork found with given ID!");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewPage = new ViewPage();
    viewPage.mount();
};

window.addEventListener('DOMContentLoaded', main);

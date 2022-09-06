import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import DeleteArtworkClient from "../api/deleteArtworkClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class DeletePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onDelete', 'renderExample'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('delete-artwork-form').addEventListener('submit', this.onDelete);

        this.client = new DeleteArtworkClient();

        this.dataStore.addChangeListener(this.renderExample)
    }

    // Render Methods --------------------------------------------------------------------------------------------------
    async renderExample() {

        let resultArea = document.getElementById("delete-artwork-form");

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
                    Is Sold: ${artwork.sold}<br>
                    This artwork has been deleted!<br></label>
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
                                       <img src="https://artsworcester.org/wp-content/uploads/2020/06/Jennessa-Burks_Jennessa_Burks_2020.jpg">
                            <label style="text-align: center">
                                                                <a href="mailto:email@sample.com">CONTACT US</a>
                                                                </label>
                    </body>
            `
        } else {
            resultArea.innerHTML = "No Item";
        }
    }

    async onDelete(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        let id = document.getElementById("delete-artwork-id").value;
        this.dataStore.set("artwork", null);
        let result = await this.client.getArtwork(id, this.errorHandler);
        this.dataStore.set("artwork", result);
        if (result) {
            this.showMessage(`Found artwork ${result.title}!`);
            this.client.deleteArtwork(id);
            this.showMessage(`Deleted artwork ${result.title}`);
        } else {
            this.errorHandler("No artwork found with given ID!");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const deletePage = new DeletePage();
    deletePage.mount();
};

window.addEventListener('DOMContentLoaded', main);

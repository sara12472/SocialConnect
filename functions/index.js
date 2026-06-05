

const {setGlobalOptions} = require("firebase-functions");
const {onRequest} = require("firebase-functions/https");

const functions = require("firebase-functions");
const admin = require("firebase-admin");

admin.initializeApp();

const functions = require("firebase-functions");
const admin = require("firebase-admin");

admin.initializeApp();

exports.sendLikeNotification = functions.firestore
    .document("posts/{postId}/likes/{userId}")
    .onCreate(async (snap, context) => {

        const postId = context.params.postId;
        const likerId = context.params.userId;

        // 1. Get post data (owner id)
        const postDoc = await admin.firestore()
            .collection("posts")
            .doc(postId)
            .get();

        const postOwnerId = postDoc.data().userId;

        // 2. Get liker user data (username)
        const likerDoc = await admin.firestore()
            .collection("users")
            .doc(likerId)
            .get();

        const likerUsername = likerDoc.data().username || "Someone";

        // 3. Get post owner token
        const ownerDoc = await admin.firestore()
            .collection("users")
            .doc(postOwnerId)
            .get();

        const token = ownerDoc.data().fcmToken;

        if (!token) return null;

        // 4. Send notification with username
        const message = {
            notification: {
                title: "❤️ New Like",
                body: `${likerUsername} liked your post`
            },
            token: token
        };

        return admin.messaging().send(message);
    });



setGlobalOptions({ maxInstances: 10 });

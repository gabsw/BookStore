BRANCH_NAME=$GITHUB_REF##*/}
PR_KEY=$(jq --raw-output .pull_request.number "$GITHUB_EVENT_PATH")
BASE_BRANCH_NAME=master

echo $BRANCH_NAME
echo $PR_KEY
echo $BASE_BRANCH_NAME


mvn clean verify sonar:sonar --file bestofbooks/pom.xml -Dsonar.projectKey=gabsw_BookStore \
	-Dsonar.organization=gabsw -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=$SONAR_TOKEN \
	-Dsonar.pullrequest.key=$PR_KEY -Dsonar.pullrequest.base=master

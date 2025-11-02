#!/bin/bash

echo "🔧 Setting up Jenkins job with GitHub webhook..."

JENKINS_URL="http://localhost:8080"
JOB_NAME="ftms-backend-pipeline"
GITHUB_REPO="https://github.com/ftms-banking/ftms-backend.git"

# Create Jenkins job using CLI
cat > /tmp/jenkins-job.xml << 'EOF'
<?xml version='1.1' encoding='UTF-8'?>
<flow-definition plugin="workflow-job@2.40">
  <description>FTMS Backend - Automated CI/CD Pipeline</description>
  <keepDependencies>false</keepDependencies>
  <properties>
    <org.jenkinsci.plugins.workflow.job.properties.PipelineTriggersJobProperty>
      <triggers>
        <hudson.triggers.SCMTrigger>
          <spec>H/2 * * * *</spec>
          <ignorePostCommitHooks>false</ignorePostCommitHooks>
        </hudson.triggers.SCMTrigger>
      </triggers>
    </org.jenkinsci.plugins.workflow.job.properties.PipelineTriggersJobProperty>
  </properties>
  <definition class="org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition">
    <scm class="hudson.plugins.git.GitSCM">
      <configVersion>2</configVersion>
      <userRemoteConfigs>
        <hudson.plugins.git.UserRemoteConfig>
          <url>GITHUB_REPO_PLACEHOLDER</url>
        </hudson.plugins.git.UserRemoteConfig>
      </userRemoteConfigs>
      <branches>
        <hudson.plugins.git.BranchSpec>
          <name>*/main</name>
        </hudson.plugins.git.BranchSpec>
        <hudson.plugins.git.BranchSpec>
          <name>*/develop</name>
        </hudson.plugins.git.BranchSpec>
        <hudson.plugins.git.BranchSpec>
          <name>*/feature/*</name>
        </hudson.plugins.git.BranchSpec>
      </branches>
      <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>
      <submoduleCfg class="list"/>
    </scm>
    <scriptPath>Jenkinsfile</scriptPath>
    <lightweight>true</lightweight>
  </definition>
  <triggers/>
  <disabled>false</disabled>
</flow-definition>
EOF

# Replace placeholder
sed -i "s|GITHUB_REPO_PLACEHOLDER|${GITHUB_REPO}|g" /tmp/jenkins-job.xml

# Create job using Jenkins API
curl -X POST "${JENKINS_URL}/createItem?name=${JOB_NAME}" \
  --user admin:admin123 \
  --header "Content-Type: application/xml" \
  --data-binary @/tmp/jenkins-job.xml

echo "✅ Jenkins job created!"
echo "📊 View job: ${JENKINS_URL}/job/${JOB_NAME}/"

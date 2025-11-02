name: ✨ Feature Request
description: Propose a new feature or enhancement for the application.
labels: [enhancement, needs-triage]
title: "[FEATURE] <concise title>"
assignees: ""
body:
  - type: markdown
    attributes:
      value: |
        ## Feature Proposal
        Please provide a high-level overview of the new functionality.
  - type: textarea
    id: motivation
    attributes:
      label: Business Motivation
      description: Why is this feature needed? What value does it bring?
  - type: textarea
    id: requirements
    attributes:
      label: Functional Requirements
      description: Outline the expected behavior or acceptance criteria.
  - type: textarea
    id: design
    attributes:
      label: Design Considerations
      description: Mention any architectural impacts, dependencies, or integrations.
  - type: dropdown
    id: priority
    attributes:
      label: Priority
      options:
        - P0 - Immediate
        - P1 - High
        - P2 - Medium
        - P3 - Low
  - type: input
    id: stakeholders
    attributes:
      label: Key Stakeholders
      description: List the teams or roles impacted by this feature.
  - type: textarea
    id: notes
    attributes:
      label: Additional Notes
      description: Any additional context, diagrams, or reference documents.

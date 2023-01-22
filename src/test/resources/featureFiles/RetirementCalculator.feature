Feature: Verify retirement calculator

  @tc001 @regression
  Scenario: [TC_001] User should be able to submit form with all required fields filled in
    Given User is available on calulator screen
    When User provide all input details
    And social security benefit select as No
    And click calculate button
    Then result message should be displayed
    
	@tc002 @regression
  Scenario: [TC_002] Additional Social Security fields should display/hide based on Social Security benefits toggle
    Given User is available on calulator screen
    When User provide all input details
    And social security benefit select as Yes
    Then martial status section should be displayed
    And social security benefit select as No
    Then martial status section should not be displayed

	 @tc003 @regression
  Scenario: [TC_003] User should be able to submit form with all fields filled in
    Given User is available on calulator screen
    When User provide all input details
    And social security benefit select as Yes
    Then martial status section should be displayed
    And click calculate button
    Then result message should be displayed
    
   @tc004 @regression
  Scenario: [TC_004] User should be able to update default calculator values
    Given User is available on calulator screen
    When User provide all input details
		And social security benefit select as No
		And click on Adjust default values link
    And user provide Default calculator values
    And click save changes button
    And click calculate button
    Then result message should be displayed

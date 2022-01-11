# Meal Planner
## plan meals and give summary
### What the  application will do
The application will track the user's meal plan for the week. 
The *ingredients list* will keep the ingredient name and the cost. 
The *grocery list* will show what ingredients the user need to buy
and the total cost of the groceries. When the groceries are purchase,
it will be sent to the *pantry list*. The *daily meal plan* will keep 
track of what the user will eat in a day. Lastly, *weekly meal plan* 
will give a summary of what the user eat in a week and view all the
items the user need to buy.

### What the **Application** Tracks:
- Ingredients list
- Pantry List
- Grocery List
- Daily Meal Plan
- Weekly Meal Plan

### For Whom?
Anyone interested in tracking their **grocery expenses** will be able to use 
this application. It will help users to decide what they are going to eat and 
will show how much expenses they pay for their own groceries.

### Purpose
This project is of interest to me because I live by myself for quite a while, 
and I still have difficulty in managing my expenses, especially grocery expenses. 
When the season changes, my appetite also increases. There are some days when 
I eat a lot and there are some days when I eat less, so it would be helpful
if I can plan out what I eat in a week.

## User Stories
- As a user, I want to be able to add ingredients to grocery list
- As a user, I want to be able to view ingredients in the grocery list
- As a user, I want to be able to add meal plans in every day
- As a user, I want to be able to remove meal plans in certain day and time
- As a user, I want to be able to view meal plans in a week
- As a user, I want to be able to automatically add ingredient information from loading the ingredients file
- As a user, I want to be able to save my meal plan
- As a user, I want to be able to save my grocery list

##Phase 4: Task 2

Wed Nov 24 17:33:49 PST 2021
bread is set as lunch on Sunday

Wed Nov 24 17:33:49 PST 2021
fish and chips is set as dinner on Sunday

Wed Nov 24 17:34:11 PST 2021
Breakfast is cleared

Wed Nov 24 17:34:23 PST 2021
Eggs is added to grocery

Wed Nov 24 17:34:34 PST 2021
Ingredients are added to pantry

Wed Nov 24 17:34:34 PST 2021
Ingredients are move to pantry

##Phase 4: Task 3
- Delete DailyMealPlan class : Since there is always seven days a week, it is
possible to initialize the WeeklyMealPlan with seven days. So, the WeeklyMealPlan
still contains a list of days but in String, which will make it easier to modify.
- reconsider having GroceryList, PantryList, and IngredientList in one class : 
all three classes have similar functions
- changing the public classes inside a class to private because it is not necessary
to be used by other classes
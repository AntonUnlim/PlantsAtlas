package com.unlim.plantsatlas.data;

public class Const {
    public static final String INTENT_IS_LAT_NAMES = "lat";
    public static final String INTENT_SECTION = "category";
    public static final String INTENT_CATEGORY_LIST_FOR_PLANTS = "categoryToFilterPlants";
    public static final String INTENT_PLANT_ID = "plant_id";
    public static final String INTENT_PHOTO = "photo_id";
    
    private static final String ABOUT_TEXT = "Перед Вами открывается уникальная возможность путешествия в разнообразный мир дикорастущих растений Саратовского края. Это виды лесные, степные и даже полупустынные. АТЛАС будет служить Вам путеводителем. Красочный дизайн и удобная навигация по электронному пособию помогут в этом. Приятного путешествия!\nПособие содержит авторские фотографии и описания более 700 видов растений, их латинские названия, принадлежность к семейству, морфологические и определительные признаки, время цветения, значение в жизни человека. Отдельно представлены растения Красной книги Саратовской области и Российской Федерации.\nНужное растение Вы можете найти разными путями, по: русскому названию, латинскому, семейству, месту обитания, жизненным формам (деревья, кустарники, травы), окраске венчика цветка. Фотографию растения можно увеличить, щелкнув по ней мышкой.\nРазнообразие нашей флоры интересует многих. Атлас будет полезен учителям биологии, экологии, географии, педагогам дополнительного образования, школьникам, студентам, всем взрослым любителям природы и юным начинающим натуралистам.\nДанный АТЛАС – уникальный, не имеющий аналогов в Саратовской области информационный ресурс. Он создан небольшой группой энтузиастов общественной детской организации «Союз юных экологов Саратовской области». Использованы авторский фотоматериал и многолетние наблюдения, сделанные в экспедициях по всей области. Авторы фотографий: Р.Л.Сосновская ( Почетный работник образования, учитель биологии Физико-технического лицея №1 г.Саратова), Л.П.Худякова (краевед, ботаник, методист Областного детского экоцентра), А.Н.Башкатов (канд. геогр.наук, доцент СГУ), В.А.Кошкин (профессиональный фотохудожник-натуралист), М.Л.Мухаметжанова (Почетный работник образования, учитель биологии СОШ №6 г.Саратова), Г.Ф.Игошина (учитель биологии Лицея №4 г.Саратова). Авторы-составители описаний Л.П.Худякова, Р.Л.Сосновская, М.Л.Мухаметжанова. Технический оператор М.Л.Мухаметжанова, Главный редактор – Р.Л.Сосновская. Разработчик программного обеспечения – доктор физ.-мат. наук, профессор СГУ, генеральный директор Научно-образовательного центра «Эрудит» Абросимов Михаил Борисович.\n\nАвторы выражают благодарность рецензентам первого издания Атласа докт. биол.наук, профессору М.А.Березуцкому, докт.биол.наук, профессору Ю.И.Буланому, канд. биол.наук Т.Б.Решетниковой, консультанту Л.А.Серовой, преподавателю Политехнического университета, особая благодарность за помощь в определении видов растений – Е.А.Кирееву, известному знатоку Саратовской флоры.\n\nПри составлении описаний растений были использованы следующие литературные источники:\n\nЕленевский А.Г., Буланый Ю.И., Радыгина В.И. Определитель сосудистых растений Саратовской области. – Саратов: Изд-во «ИП Баженов». 2009. – 248 с.: Библиогр.5 назв.\nМаевский П.Ф. Флора Средней полосы Европейской части СССР.-Сельхозгиз, Ленинград, 1964.\nСерова Л.А., Березуцкий М.А. Растения национального парка «Хвалынский» (конспект флоры). – Саратов: Изд-во «Научная книга», 2008.-194 с.: ил.32 с.\nКрасная книга Саратовской области. – Саратов: Изд-во Торгово-промышленной палаты Саратов. обл., 2006.\n\nПредполагается дальнейшая работа над Атласом с использованием авторских фотоматериалов новых полевых сезонов. Ваши отзывы и предложения просим направлять по адресу ekokonkurs@mail.ru\nИнформацию о «Союзе юных экологов» вы можете посмотреть на сайтах geografika.sgu.ru и sar-un-eco.sgu.ru.\n";
    public static String getTextAbout() {
        return "\t" + ABOUT_TEXT.replace("\n", "\n\t");
    }
}
